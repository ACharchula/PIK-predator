import React from 'react';
import SideNav from 'react-simple-sidenav';
import SideNavItems from './SideNavItems';
import PriceBars from './PriceBar/PriceBars'
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
                <PriceBars {...props} />
                <SideNavItems {...props}/>
            </SideNav>
        </div>
    )
}

export default SideNavigation;