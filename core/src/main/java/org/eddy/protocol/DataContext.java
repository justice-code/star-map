package org.eddy.protocol;

import lombok.*;
import org.eddy.url.URL;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class DataContext implements Serializable{
    private static final long serialVersionUID = -5944882935573697136L;

    @NonNull
    private URL url;
    @NonNull
    private String caller;
    private Map<String, String> attachments = new HashMap<>();
}
