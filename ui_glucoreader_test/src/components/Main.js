import {useEffect, useState} from "react";

import style from './Main.module.css'
import { FcOk } from 'react-icons/fc'
import Section from "./Section";
import ComPortList from "./ComPortList";
import {onOpen, onClose, onError, onMessage, connectionListener} from './web/wsConnection';

let countRender = 0;

function Main() {
      let [comPortList, setComPortList] = useState([
            {
                  id: null,
                  name: "",
                  description: ""
            }
      ]);

      useEffect(() => {
            console.log("ComPortList : "  + comPortList);
            connectionListener(setComPortList, comPortList);
      }, []);

      let [activeComPortName, setActiveComPortName] = useState("");
      console.log("Main countRender : " + countRender++);

      const setActiveComPortNameHandler = (name) => {
                  name === activeComPortName
                        ? setActiveComPortNameHandler("")
                        : setActiveComPortName(name);
      };
      console.log("activeComPortName : " + activeComPortName);

      return (
            <div className={style.main}>
                  <ComPortList
                        text={"Список COM портов:"}
                        comPortList={comPortList}
                        activeComPortName={activeComPortName}
                        setActiveComPortNameHandler={setActiveComPortNameHandler}
                  />
                  <Section text={"Соединение:"}>
                        <FcOk/>
                  </Section>
                  <Section text={"Версия протокола:"}/>
                  <Section text={"Версия прибора:"}/>
                  <Section text={"Состояние прибора:"}/>
            </div>
      )
}

export default Main;