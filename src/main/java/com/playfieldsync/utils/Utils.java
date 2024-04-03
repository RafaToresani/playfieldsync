package com.playfieldsync.utils;

import org.apache.coyote.BadRequestException;

public class Utils {

    public static void checkId(Long id, String resource) throws BadRequestException {
        if(id==null) throw new BadRequestException("ERROR. El id del " + resource + " no puede ser nulo");
        if(id<=0) throw new BadRequestException("ERROR. El id del " + resource + " no puede ser menor o igual 0");

    }
}
