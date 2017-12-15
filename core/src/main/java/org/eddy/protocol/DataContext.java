package org.eddy.protocol;

import lombok.*;
import org.eddy.url.URL;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DataContext implements Serializable{
    private static final long serialVersionUID = -5944882935573697136L;

    private URL url;
    private transient String side;
    private String caller;
}
