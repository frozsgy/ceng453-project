package group10.client.enums;

import group10.client.constants.GameConstants;

public enum MatchType {
    NO(0),
    REGULAR(1),
    PISTI(GameConstants.PISTI),
    DOUBLE_PISTI(GameConstants.DOUBLE_PISTI);

    private int score;

    MatchType(int score) {
        this.score = score;
    }
}
