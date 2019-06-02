import React from 'react';
import './SideNavigation.scss';

const SideNavItems = (props) => {

    const items = [
        {
            type: "option",
            text: 'CPUs',
        },
        {
            type: "option",
            text: 'Storage'
        },
        {
            type: "option",
            text: 'Display',
        }
    ]

    const showItems = () => {
        return items.map( (item, i)=> {
            return(
                <div className={item.type} key={i}>
                    {item.text}
                </div>
            )
        })
    }

    return(
       <div>
           {showItems()}
       </div>
    )
}

export default SideNavItems;