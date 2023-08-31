import {memo, useEffect, useState} from "react";

import style from './Main.module.css'
import getComPortList from "./web/getComPortList";
import { FcOk } from 'react-icons/fc'
import Section from "./Section";
import Title from "./Title";

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
            setComPortList(getComPortList);

      }, [setComPortList]);

      let [activeComPortName, setActiveComPortName] = useState("");
      console.log(countRender++);
      return (
            <div className={style.main}>
                  <Title
                        text={"Список COM портов:"}
                        comPortList={comPortList}
                        setComPortList={setComPortList}
                        activeComPortName={setActiveComPortName}
                        setActiveComPortName={setActiveComPortName}
                  />
                  <Section text={"Соединение:"}>
                        <FcOk/>
                  </Section>
                  <Section text={"Версия протокола:"}>
                  </Section>
                  <Section text={"Версия прибора:"}/>
                  <Section text={"Состояние прибора:"}/>
            </div>
      )
}

export default Main;