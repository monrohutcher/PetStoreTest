package com.example.sandbox.util.constans;

import java.util.Arrays;
import java.util.List;

public class PetStatus {
    public static final String AVAILABLE = "available";
    public static final String PENDING = "pending";
    public static final String SOLD = "sold";

    public static final List<String> statusList = Arrays.asList(
            AVAILABLE,
            PENDING,
            SOLD
    );
}
