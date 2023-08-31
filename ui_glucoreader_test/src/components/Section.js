import style from './Section.module.css'

function Section({text, children}) {
      return (
            <div className={style.section}>
                  <div className={style.text}>
                        {text}
                  </div>
                  {children}
            </div>
      )
}

export default Section;