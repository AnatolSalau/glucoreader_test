import style from './DeviceWindow.module.css'
import Section from "./Section";
import {FcOk} from "react-icons/fc";

function DeviceWindow() {
      return (
            <div className={style.deviceWindow}>
                  <Section text={"Соединение:"}>
                        <FcOk/>
                  </Section>
                  <Section text={"Версия протокола:"}/>
                  <Section text={"Версия прибора:"}/>
                  <Section text={"Состояние прибора:"}/>
            </div>
      )
}

export default DeviceWindow;