import style from './Title.module.css'
import CheckBoxList from "./checkBoxList/CheckBoxList";

function Title({text, activeComPortName, setActiveComPortName, comPortList, setComPortList}) {
      return (
            <div className={style.title}>
                  <div className={style.text}>
                        {text}
                  </div>
                  <CheckBoxList
                        activeComPortName={activeComPortName}
                        setActiveComPortName={setActiveComPortName}
                        comPortList={comPortList}
                        setComPortList={setComPortList}
                  />
            </div>
      )
}

export default Title;