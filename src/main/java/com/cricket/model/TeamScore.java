package com.cricket.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamScore {
    private int id;
    private int balls;
    private int overs;
    private int runs;
    private  int wickets;

    public boolean isOverBreak() {
        return overs > 0 && balls == 0;
    }
}
