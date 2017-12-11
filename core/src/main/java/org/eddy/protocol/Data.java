package org.eddy.protocol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Data implements Serializable {

    private static final long serialVersionUID = -5843097650946558124L;

    private String id;

    private String script;
}
