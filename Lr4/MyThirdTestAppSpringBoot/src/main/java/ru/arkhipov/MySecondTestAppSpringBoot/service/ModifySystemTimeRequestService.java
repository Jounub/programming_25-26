package ru.arkhipov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.arkhipov.MySecondTestAppSpringBoot.model.Request;
import ru.arkhipov.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.util.Date;

@Service
public class ModifySystemTimeRequestService implements ModifyRequestService{
    @Override
    public void modify(Request request){
        request.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));
    }
}