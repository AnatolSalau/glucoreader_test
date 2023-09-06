import style from './ComPortList.module.css'
import CheckBoxList from "./checkBoxList/CheckBoxList";

function ComPortList({text, activeComPortName, setActiveComPortName, comPortList}) {
      return (
            <div className={style.comPortList}>
                  <div className={style.text}>
                        {text}
                  </div>
                  <CheckBoxList
                        activeComPortName={activeComPortName}
                        setActiveComPortName={setActiveComPortName}
                        comPortList={comPortList}
                  />
            </div>
      )
}

export default ComPortList;