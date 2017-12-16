package org.eddy.protocol;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Data implements Serializable {

    private static final long serialVersionUID = -5843097650946558124L;

    @NonNull
    private String id;

    private String script;

    @NonNull
    private boolean success;

    private DataContext context;

    private DataResponse response;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        return id.equals(data.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
