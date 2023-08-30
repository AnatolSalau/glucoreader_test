import style from './Section.module.css'

function Section({text}) {
      return (
            <div className={style.section}>
                  <div className={style.text}>
                        {text}
                  </div>
            </div>
      )
}

export default Section;