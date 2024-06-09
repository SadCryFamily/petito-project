package com.petito.project.constants;

public enum SQLFieldConstants
{
    PASSWORD(255), ANY(45);

    private int fieldSize;
    SQLFieldConstants(int size)
    {
        this.fieldSize = size;
    }

    public static Boolean isCorrectPassword(String password)
    {
        return PASSWORD.fieldSize > password.length();
    }

    public static Boolean isCorrectTextField(String text)
    {
        return ANY.fieldSize <= text.length();
    }
}
