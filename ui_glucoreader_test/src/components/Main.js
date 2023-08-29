import style from './Main.module.css'
import { FcOk } from 'react-icons/fc'
import Section from "./Section";
import Title from "./Title";


function Main() {
      return (
            <div className={style.main}>
                  <Title>
                        <FcOk/>
                  </Title>
                  <Section text={"Версия протокола:"}/>
                  <Section text={"Версия прибора:"}/>
                  <Section text={"Состояние прибора:"}/>
            </div>
      )
}

export default Main;