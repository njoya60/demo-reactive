package com.wf.br.aa;

import com.wf.br.model.Societe;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static Societe dtoToEntity(SocieteDto societeDto){
        Societe societe=new Societe();
        BeanUtils.copyProperties(societeDto,societe);
        return societe;
    }

    public static SocieteDto entityToDto(Societe societe){
       return new SocieteDto(societe.getId(),societe.getName(),societe.getPrice());
    }
}
