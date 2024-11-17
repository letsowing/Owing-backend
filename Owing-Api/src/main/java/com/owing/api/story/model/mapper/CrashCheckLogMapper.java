package com.owing.api.story.model.mapper;

import com.owing.api.story.model.dto.response.CrashCheckItemResponse;
import com.owing.api.story.model.dto.response.CrashCheckLogResponse;
import com.owing.api.story.model.dto.response.CrashCheckResponse;
import com.owing.common.annotation.Mapper;
import com.owing.entity.domains.ai.log.story.model.CrashCheckLog;
import com.owing.entity.domains.ai.log.story.model.CrashCheckOutput;
import com.owing.entity.domains.story.model.Story;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Mapper
@RequiredArgsConstructor
@Slf4j
public class CrashCheckLogMapper {

    public CrashCheckOutput toSpellCheckOutput(CrashCheckResponse.Items crashItems) {
        return CrashCheckOutput.builder()
                .add(crashItems.getAdd())
                .base(crashItems.getBase())
                .reason(crashItems.getReason())
                .build();
    }

    public CrashCheckLog toEntity(Story story, CrashCheckResponse aiResponse) {
        List<CrashCheckOutput> outputList = Arrays.stream(aiResponse.getItems()).map(this::toSpellCheckOutput).toList();
        return new CrashCheckLog(story, outputList);
    }

    public CrashCheckItemResponse toCrashCheckItemResponse(CrashCheckOutput crashCheckOutput) {
        return new CrashCheckItemResponse(
                crashCheckOutput.base(),
                crashCheckOutput.add(),
                crashCheckOutput.reason()
        );
    }

    public CrashCheckLogResponse toLogResponse(CrashCheckLog crashCheckLog) {
        List<CrashCheckItemResponse> list = crashCheckLog.getOutput().stream().map(this::toCrashCheckItemResponse).toList();
        return new CrashCheckLogResponse(
                crashCheckLog.getId(),
                list,
                crashCheckLog.getCreatedAt()
        );
    }

    public List<CrashCheckLogResponse> toLogListResponse(List<CrashCheckLog> crashCheckLogList) {
        return crashCheckLogList.stream()
                .map(this::toLogResponse)
                .toList();
    }
}
