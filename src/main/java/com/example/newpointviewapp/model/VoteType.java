package com.example.newpointviewapp.model;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1);

    private int direction;

    VoteType(int direction) {
    }

    public static VoteType lookup(Integer direction) {
        return null;
//                Arrays.stream(VoteType.values())
//                .filter(value -> value.getDirection().equals(direction))
//                .findAny()
//                .orElseThrow(() -> new PointOfViewExeption("Vote not found"));
    }

    private Integer getDirection() {
        return direction;
    }


}
