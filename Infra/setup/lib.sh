#!/usr/bin/env bash

# 로그 메시지 출력 함수
function log {
    echo "[+] $1"
}

# 하위 로그 메시지 출력 함수
function sublog {
    echo "   ⠿ $1"
}

# 에러 메시지 출력 함수
function err {
    echo "[x] $1" >&2
}

# 하위 에러 메시지 출력 함수
function suberr {
    echo "   ⠍ $1" >&2
}

# Elasticsearch 서비스가 HTTP 200을 반환할 때까지 대기
function wait_for_elasticsearch {
    local elasticsearch_host="${ELASTICSEARCH_HOST:-elasticsearch}"

    local -a args=( '-s' '-D-' '-m15' '-w' '%{http_code}' "http://${elasticsearch_host}:9200/" )

    if [[ -n "${ELASTIC_PASSWORD:-}" ]]; then
        args+=( '-u' "elastic:${ELASTIC_PASSWORD}" )
    fi

    local -i result=1
    local output

    # 최대 300초(60회 * 5초)까지 재시도
    for _ in $(seq 1 60); do
        local -i exit_code=0
        output="$(curl "${args[@]}")" || exit_code=$?

        if ((exit_code)); then
            result=$exit_code
        fi

        if [[ "${output: -3}" -eq 200 ]]; then
            result=0
            break
        fi

        sleep 5
    done

    if ((result)) && [[ "${output: -3}" -ne 000 ]]; then
        echo -e "\n${output::-3}"
    fi

    return $result
}

# Elasticsearch 사용자 API가 사용자 목록을 반환할 때까지 대기
function wait_for_builtin_users {
    local elasticsearch_host="${ELASTICSEARCH_HOST:-elasticsearch}"

    local -a args=( '-s' '-D-' '-m15' "http://${elasticsearch_host}:9200/_security/user?pretty" )

    if [[ -n "${ELASTIC_PASSWORD:-}" ]]; then
        args+=( '-u' "elastic:${ELASTIC_PASSWORD}" )
    fi

    local -i result=1
    local line
    local -i exit_code=0
    local -i num_users=0

    # 최대 30초(30회 * 1초)까지 재시도
    for _ in $(seq 1 30); do
        num_users=0

        # 커맨드 서브스티튜션을 사용하여 출력과 종료 코드를 캡처
        output="$(curl "${args[@]}"; printf '%s' "$?")"
        exit_code="${output: -1}"
        response="${output::-1}"

        if ((exit_code)); then
            result=$exit_code
        fi

        # '_reserved": true' 패턴을 찾음
        num_users=$(echo "$response" | grep -c '"_reserved" : true')

        # 'elastic' 사용자 외에 추가 사용자가 있는지 확인
        if (( num_users > 1 )); then
            result=0
            break
        fi

        sleep 1
    done

    return $result
}

# 지정한 Elasticsearch 사용자가 존재하는지 확인
function check_user_exists {
    local username=$1

    local elasticsearch_host="${ELASTICSEARCH_HOST:-elasticsearch}"

    local -a args=( '-s' '-D-' '-m15' '-w' '%{http_code}' "http://${elasticsearch_host}:9200/_security/user/${username}" )

    if [[ -n "${ELASTIC_PASSWORD:-}" ]]; then
        args+=( '-u' "elastic:${ELASTIC_PASSWORD}" )
    fi

    local output
    output="$(curl "${args[@]}")"
    local http_code="${output: -3}"
    local response="${output::-3}"

    if [[ "$http_code" -eq 200 ]]; then
        echo 1
        return 0
    elif [[ "$http_code" -eq 404 ]]; then
        echo 0
        return 0
    else
        echo -e "\n$response"
        return 1
    fi
}

# 지정한 Elasticsearch 사용자의 비밀번호 설정
function set_user_password {
    local username=$1
    local password=$2

    local elasticsearch_host="${ELASTICSEARCH_HOST:-elasticsearch}"

    local -a args=( '-s' '-D-' '-m15' '-w' '%{http_code}' \
        "http://${elasticsearch_host}:9200/_security/user/${username}/_password" \
        '-X' 'POST' \
        '-H' 'Content-Type: application/json' \
        '-d' "{\"password\" : \"${password}\"}" \
    )

    if [[ -n "${ELASTIC_PASSWORD:-}" ]]; then
        args+=( '-u' "elastic:${ELASTIC_PASSWORD}" )
    fi

    local output
    output="$(curl "${args[@]}")"
    local http_code="${output: -3}"
    local response="${output::-3}"

    if [[ "$http_code" -eq 200 ]]; then
        return 0
    else
        echo -e "\n$response\n"
        return 1
    fi
}

# 지정한 Elasticsearch 사용자 생성
function create_user {
    local username=$1
    local password=$2
    local role=$3

    local elasticsearch_host="${ELASTICSEARCH_HOST:-elasticsearch}"

    local -a args=( '-s' '-D-' '-m15' '-w' '%{http_code}' \
        "http://${elasticsearch_host}:9200/_security/user/${username}" \
        '-X' 'POST' \
        '-H' 'Content-Type: application/json' \
        '-d' "{\"password\":\"${password}\",\"roles\":[\"${role}\"]}" \
    )

    if [[ -n "${ELASTIC_PASSWORD:-}" ]]; then
        args+=( '-u' "elastic:${ELASTIC_PASSWORD}" )
    fi

    local output
    output="$(curl "${args[@]}")"
    local http_code="${output: -3}"
    local response="${output::-3}"

    if [[ "$http_code" -eq 200 ]]; then
        return 0
    else
        echo -e "\n$response\n"
        return 1
    fi
}

# 지정한 Elasticsearch 역할을 업데이트하거나 없으면 생성
function ensure_role {
    local name=$1
    local body=$2

    local elasticsearch_host="${ELASTICSEARCH_HOST:-elasticsearch}"

    local -a args=( '-s' '-D-' '-m15' '-w' '%{http_code}' \
        "http://${elasticsearch_host}:9200/_security/role/${name}" \
        '-X' 'POST' \
        '-H' 'Content-Type: application/json' \
        '-d' "$body" \
    )

    if [[ -n "${ELASTIC_PASSWORD:-}" ]]; then
        args+=( '-u' "elastic:${ELASTIC_PASSWORD}" )
    fi

    local output
    output="$(curl "${args[@]}")"
    local http_code="${output: -3}"
    local response="${output::-3}"

    if [[ "$http_code" -eq 200 ]]; then
        return 0
    else
        echo -e "\n$response\n"
        return 1
    fi
}
