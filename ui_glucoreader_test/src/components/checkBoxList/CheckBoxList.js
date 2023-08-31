import style from './CheckBoxList.module.css'
import CheckBox from "./CheckBox";

function CheckBoxList({activeComPortName, setActiveComPortName, comPortList, setComPortList}) {
      return (
            <div className={style.checkBoxList}>
                  <CheckBox name={'COM1'} description={'GlucoBridge'}/>
                  <CheckBox name={'COM2'} />
            </div>
      )
}

export default CheckBoxList;