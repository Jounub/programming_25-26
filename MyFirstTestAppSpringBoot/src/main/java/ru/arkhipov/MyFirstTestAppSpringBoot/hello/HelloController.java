package ru.arkhipov.MyFirstTestAppSpringBoot.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class HelloController {
    private ArrayList stringList = null;
    private HashMap hashMap = null;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name",
    defaultValue = "World") String name){
        return String.format("Hello %s!", name);
    }
    @GetMapping("/update-array")
    public void updateArray(@RequestParam String value){
        if(stringList == null){
            stringList = new ArrayList<>();
        }
        stringList.add(value);;
    }

    @GetMapping("/show-array")
    public String showArray(){
        return stringList.toString();
    }

    @GetMapping("/update-map")
    public void updateHashMap(@RequestParam Integer key, @RequestParam String value){
        if(hashMap == null){
            hashMap = new HashMap<>();
        }
        hashMap.put(key, value);
    }

    @GetMapping("/show-map")
    public String showHashMap(){
        return hashMap.toString();
    }

    @GetMapping("/show-all-length")
    public String showAllLength(){
        return "Array list length: " + stringList.size() + "; Hashmap length " + hashMap.size();
    }
}
