import React from 'react';
import SideNav from 'react-simple-sidenav';
import SideNavItems from './SideNavItems';
import { BackgroundColor } from '../../../config';

const SideNavigation = (props) => {
    return(
        <div>
            <SideNav
                showNav={props.showNav}
                onHideNav={props.onHideNav}
                className="side-nav"
                navStyle={{
                    background:BackgroundColor,
                    maxWidth:'220px',
                }}
            >
                <SideNavItems/>
            </SideNav>
        </div>
    )
}

export default SideNavigation;