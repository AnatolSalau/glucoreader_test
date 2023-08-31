import {MdOutlineCheckBoxOutlineBlank} from 'react-icons/md';
import {MdOutlineCheckBox} from 'react-icons/md';
import style from "./CheckBox.module.css";

function CheckBox({name, description}) {
      const setActiveCheckBoxOnCLick = (click) => {
            
      }
      return (
            <div className={style.checkBox}>
                  <div className={style.checkFieldEmpty}>
                        <MdOutlineCheckBoxOutlineBlank/>
                  </div>
                  <div className={style.nameDescription}>
                        <div className={style.name}>
                              {name}
                        </div>
                        <div className={style.description}>
                              {description}
                        </div>
                  </div>


            </div>
      )
}

export default CheckBox;