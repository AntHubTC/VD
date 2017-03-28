package com.tc.vd.service;

import com.tc.vd.config.ResConstant;
import com.tc.vd.model.ContactGoalConfig;
import com.tc.vd.utils.FileResUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 地址簿
 * Created by tangcheng on 2017/3/16.
 */
public class AddressBook {
    private static AddressBook addressBook;
    //地址簿数据列表
    public static ObservableList<ContactGoalConfig> addressBookList = FXCollections.observableArrayList();

    private AddressBook(){}

    /**
     * 获取实例
     * @return
     */
    public static synchronized AddressBook getInstance(){
        if(null == addressBook){
            addressBook = new AddressBook();
        }
        return addressBook;
    }

    /**
     * 加载数据
     */
    public void loadData(){
        addressBookList.clear();
        try {
            File resFile = FileResUtil.getResFile(ResConstant.rootPath, ResConstant.addressBook);
            HierarchicalINIConfiguration context = new HierarchicalINIConfiguration();
            context.load(resFile);
            //转换数据
            for (String sectionName : context.getSections()){
                SubnodeConfiguration section = context.getSection(sectionName);
                ContactGoalConfig contactGoalConfig = ContactGoalConfig.create(section);

                addressBookList.add(contactGoalConfig);
            }
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        测试数据
//        addressBookList.add(new ContactGoalConfig("地址簿测试1","http","127.0.0.1",8081));
//        addressBookList.add(new ContactGoalConfig("地址簿测试2","http","127.0.0.2",8081));
    }

    /**
     *  持久化地址簿数据
     */
    public void persistData(){
        try {
            File resFile = FileResUtil.getResFile(ResConstant.rootPath, ResConstant.addressBook);
            HierarchicalINIConfiguration context = new HierarchicalINIConfiguration();
            //转换数据
            for (int i = 0; i < addressBookList.size(); i++) {
                String sectionName = String.valueOf(i);
                addressBookList.get(i).appendTo(context, sectionName);
            }

            //保存数据
            context.save(resFile);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ContactGoalConfig> getAddressBookList() {
        return addressBookList;
    }

    public void setAddressBookList(ObservableList<ContactGoalConfig> addressBookList) {
        AddressBook.addressBookList = addressBookList;
    }
}
