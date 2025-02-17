package com.example.appenglish.Domain.Model;

public class UiState<T> {

    public final boolean isLoading;
    public final T data;
    public final String error;

    private UiState(boolean isLoading, T data, String error) {
        this.isLoading = isLoading;
        this.data = data;
        this.error = error;
    }

    public static <T> UiState<T> loading() {
        return new UiState<>(true, null, null);
    }

    public static <T> UiState<T> success(T data) {
        return new UiState<>(false, data, null);
    }

    public static <T> UiState<T> error(String error) {
        return new UiState<>(false, null, error);
    }
}
