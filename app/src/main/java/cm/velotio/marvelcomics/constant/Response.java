package cm.velotio.marvelcomics.constant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Response {

    public enum Status {
        LOADING,
        SUCCESS,
        ERROR
    }

    public final Status status;

    @Nullable
    public final String data;

    @Nullable
    public final String error;

    private Response(Status status, @Nullable String data, @Nullable String error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static Response loading() {
        return new Response(Status.LOADING, null, null);
    }

    public static Response success(@NonNull String data) {
        return new Response(Status.SUCCESS, data, null);
    }

    public static Response error(@NonNull String error) {
        return new Response(Status.ERROR, null, error);
    }
}
