import style from './ComPortList.module.css'
import CheckBoxList from "./CheckBoxList";
function ComPortList({text, activeComPortName, setActiveComPortNameHandler, comPortList}) {

      return (
            <div className={style.comPortList}>
                  <div className={style.text}>
                        {text}
                  </div>
                  <CheckBoxList
                        activeComPortName={activeComPortName}
                        setActiveComPortNameHandler={setActiveComPortNameHandler}
                        comPortList={comPortList}
                  />
            </div>
      )
}

export default ComPortList;