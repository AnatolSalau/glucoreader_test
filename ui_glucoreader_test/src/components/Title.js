import style from './Title.module.css'

function Title({children}) {
      return (
            <div className={style.title}>
                  <div className={style.text}>
                        Соединение:
                  </div>

                  {children}
            </div>
      )
}

export default Title;