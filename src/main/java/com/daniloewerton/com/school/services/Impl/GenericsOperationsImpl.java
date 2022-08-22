package com.daniloewerton.com.school.services.Impl;

import com.daniloewerton.com.school.services.GenericsOperations;
import com.daniloewerton.com.school.services.exception.IllegalCpfStatementException;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericsOperationsImpl implements GenericsOperations {

    @Override
    public String validatesCpf(String cpf) {

        if (cpf.trim().length() != 11) {
            throw new IllegalCpfStatementException("Cpf Inválido.");
        }

        List<String> array = List.of(cpf.split(""));
        List<String> newArray = new ArrayList<>();

        for (int i = 0; i <= array.size() - 1; i++) {
            if (i == 2) {
                newArray.add(i, array.get(i));
                newArray.add(".");
            } else if (i == 5) {
                newArray.add(i += 1, array.get(i - 1));
                newArray.add(".");
                i -= 1;
            } else if (i == 8) {
                newArray.add(i += 2, array.get(i - 2));
                newArray.add("-");
                i -= 2;
            } else {
                newArray.add(array.get(i));
            }
        }
        return String.valueOf(newArray)
                .replace(",", "")
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "");
    }
}