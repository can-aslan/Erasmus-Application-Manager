package com.beam.beamBackend.response;

import java.util.List;

import com.beam.beamBackend.model.User;

import lombok.Data;

@Data
public class RUserList {
    int totalCount;
    List<User> list;

    public RUserList(int count, List<User> list) {
        this.totalCount = count;
        this.list = list;
    }
}
