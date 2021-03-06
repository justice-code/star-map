package org.eddy.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class DataResponse implements Serializable{

    private static final long serialVersionUID = 8291169272139930060L;

    private LocalDateTime dateTime;
    private long spend;
    private boolean success;
}
