import React from 'react';
import './SideNavigation.scss';

const SideNavItems = (props) => {

    const items = [
        {
            type: "option",
            text: 'Home',
        },
        {
            type: "option",
            text: 'News'
        },
        {
            type: "option",
            text: 'Videos',
        },
        {
            type: "option",
            text: 'Sign in',
        },
        {
            type: "option",
            text: 'Sign out',
        },
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