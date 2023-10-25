import {useEffect, useState} from "react";
import { FcOk } from 'react-icons/fc'
import style from './Main.module.css'
import Section from "./deviceWindow/Section";
import ComPortList from "./deviceList/ComPortList";
import DeviceWindow from "./deviceWindow/DeviceWindow";


function Main({connection}) {

      let [deviceList, setDeviceList] = useState([]);

      useEffect(() => {
            connection.onopen = (ev) => {
                  console.log('Connection is open');
                  if (ev.data) {
                        let json = JSON.parse(ev.data);
                        setDeviceList(json.data.deviceList);
                        console.log("onOpen" + json.data.deviceList);
                  }
            };
            connection.onmessage = (ev) => {
                  console.log('Message from server received');
                  let json = JSON.parse(ev.data);
                  setDeviceList(json.data.deviceList);
                  console.log("onMessage" + json.data.deviceList);
            };
            connection.onclose = (ev) => {
                  console.log("onClose" + 'Connection is close');
            };


      }, [deviceList]);

      let [activeDeviceName, setActiveDeviceName] = useState("");

      const setDeviceListHandler = () => {
            connection.send("getDeviceList");
      }

      const setActiveDeviceNameHandler = (name) => {
            if (name === activeDeviceName) {
                  setActiveDeviceNameHandler("")

            }
            else {
                  setActiveDeviceName(name);
                  connection.send(name);
            }
      };
      
      const setDataTimeHandler = () => {
            connection.send("setCurrentDateTime");
            console.log("setDataTimeHandler")
      }
      
      const setConverterTypeHandler = (message) => {
            console.log("setConverterTypeHandler=" + message);
            connection.send("setConverterTypeHandler=" + JSON.stringify(message));

      }
      return (
            <div className={style.main}>
                  <ComPortList
                        text={"Список COM портов:"}
                        deviceList={deviceList}
                        activeDeviceName={activeDeviceName}
                        setActiveDeviceNameHandler={setActiveDeviceNameHandler}
                        setDeviceListHandler={setDeviceListHandler}
                        setDataTimeHandler={setDataTimeHandler}
                        setConverterTypeHandler={setConverterTypeHandler}
                  />
                  <DeviceWindow activeDeviceName={activeDeviceName} deviceList={deviceList}/>
            </div>
      )
}

export default Main;