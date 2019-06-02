import React from 'react';
import './SideNavigation.scss';
import InputGroup from 'react-bootstrap/InputGroup'
import FormControl from 'react-bootstrap/FormControl'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import Form from "react-bootstrap/Form";
import axios from "axios";

//class SideNavItems extends Component {

const SideNavItems = (props) => {

    const items = [
        {
            type: "option",
            text: 'CPU',
            columnName:'processor',
            options:[]
        },
        {
            type: "option",
            text: "Hard Drive Type",
            columnName: 'hardDriveType',
            options:[]
        },
        {
            type: "option",
            text: 'Graphics Card',
            columnName: 'graphicCard',
            options:[]
        }
    ]

    items.forEach(function addOptions(item,index,array){
        console.log(item);
        console.log(item.type)
        axios.get(`https://pik-predator.herokuapp.com/catalog/metadata/${item.columnName}`)
            .then(response => item.options=response.data)
        console.log(JSON.parse(JSON.stringify(item.options)));
    });

    /*
    componentDidMount()
    {
        items.forEach(function addToString(item,index,array) {
            axios.get(`https://pik-predator.herokuapp.com/metadata/${item.text}`)
                .then(response => options=response.data)
        });
    }
    */

    const getCheckboxes = (item) =>{
        console.log(item);
        return item.options.map((option)=> {
            return(
            <Form.Check
                name={option}
                //onChange={handleChange}
                id={option}
                label={option}
            />
        )
        }
        )
    }


    const showItems = () => {

        return items.map( (item, i)=> {
            return(
                <div className={item.type} key={i}>
                    {item.text}
                    <Form>
                        <Form.Group>
                            {getCheckboxes(item)}
                        </Form.Group>
                    </Form>

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


/*
return items.map( (item, i)=> {
            return(
                <div className={item.type} key={i}>
                    {item.text}
                        <Form>
                            <Form.Group>
                                <Form.Check
                                    label={item.text}
                                />
                            </Form.Group>
                        </Form>
                </div>
            )
        })
    }
 */
/*{getCheckboxes(item)}*/
