#!/usr/bin/env bash

set -eu
set -o pipefail

# lib.sh 스크립트 소스 포함
source "$(dirname "${BASH_SOURCE[0]}")/lib.sh"

# --------------------------------------------------------
# Users declarations

declare -A users_passwords=(
    [logstash_internal]="${LOGSTASH_INTERNAL_PASSWORD:-}"
    [kibana_system]="${KIBANA_SYSTEM_PASSWORD:-}"
    [metricbeat_internal]="${METRICBEAT_INTERNAL_PASSWORD:-}"
    [filebeat_internal]="${FILEBEAT_INTERNAL_PASSWORD:-}"
    [heartbeat_internal]="${HEARTBEAT_INTERNAL_PASSWORD:-}"
    [monitoring_internal]="${MONITORING_INTERNAL_PASSWORD:-}"
    [beats_system]="${BEATS_SYSTEM_PASSWORD:-}"
)

declare -A users_roles=(
    [logstash_internal]='logstash_writer'
    [metricbeat_internal]='metricbeat_writer'
    [filebeat_internal]='filebeat_writer'
    [heartbeat_internal]='heartbeat_writer'
    [monitoring_internal]='remote_monitoring_collector'
)

# --------------------------------------------------------
# Roles declarations

declare -A roles_files=(
    [logstash_writer]='logstash_writer.json'
    [metricbeat_writer]='metricbeat_writer.json'
    [filebeat_writer]='filebeat_writer.json'
    [heartbeat_writer]='heartbeat_writer.json'
)

# --------------------------------------------------------

log 'Waiting for availability of Elasticsearch. This can take several minutes.'

declare -i exit_code=0

if ! wait_for_elasticsearch; then
    exit_code=$?
    case $exit_code in
        6)
            suberr 'Could not resolve host. Is Elasticsearch running?'
            ;;
        7)
            suberr 'Failed to connect to host. Is Elasticsearch healthy?'
            ;;
        28)
            suberr 'Timeout connecting to host. Is Elasticsearch healthy?'
            ;;
        *)
            suberr "Connection to Elasticsearch failed. Exit code: ${exit_code}"
            ;;
    esac
    exit $exit_code
fi

sublog 'Elasticsearch is running'

log 'Waiting for initialization of built-in users'

if ! wait_for_builtin_users; then
    exit_code=$?
    suberr 'Timed out waiting for condition'
    exit $exit_code
fi

sublog 'Built-in users were initialized'

for role in "${!roles_files[@]}"; do
    log "Role '$role'"

    body_file="$(dirname "${BASH_SOURCE[0]}")/roles/${roles_files[$role]:-}"
    if [[ ! -f "${body_file:-}" ]]; then
        sublog "No role body found at '${body_file}', skipping"
        continue
    fi

    sublog 'Creating/updating role'
    ensure_role "$role" "$(cat "${body_file}")"
done

for user in "${!users_passwords[@]}"; do
    log "User '$user'"
    if [[ -z "${users_passwords[$user]:-}" ]]; then
        sublog 'No password defined, skipping'
        continue
    fi

    user_exists=$(check_user_exists "$user")

    if [[ "$user_exists" -eq 1 ]]; then
        sublog 'User exists, setting password'
        set_user_password "$user" "${users_passwords[$user]}"
    else
        if [[ -z "${users_roles[$user]:-}" ]]; then
            suberr 'No role defined, skipping creation'
            continue
        fi

        sublog 'User does not exist, creating user'
        create_user "$user" "${users_passwords[$user]}" "${users_roles[$user]}"
    fi
done
