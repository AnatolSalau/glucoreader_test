import style from './Title.module.css'

function Title({children}) {
      return (
            <div className={style.title}>
                  Соединение:
                  {children}
            </div>
      )
}

export default Title;