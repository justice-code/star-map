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

}
