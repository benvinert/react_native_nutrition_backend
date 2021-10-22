package com.example.FitnessApp.Enums;

public enum ValuesEnum {
    CALORIES(0),
    PROTEIN(1),
    FAT(2),
    CARBS(3),
    COLLESTEROL(4),
    FIBER(5);

    private Integer whichValue;


    ValuesEnum(Integer whichValue) {
        this.whichValue = whichValue;
    }

}
