import style from './Section.module.css'

function Section({text}) {
      return (
            <div className={style.section}>
                  {text}
            </div>
      )
}

export default Section;