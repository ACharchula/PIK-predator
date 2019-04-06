import React from 'react'
import './LoginPage.scss'
import { Field, reduxForm } from 'redux-form';
import Button from 'react-bootstrap/Button'

const email = value =>
    value && !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(value)
        ? 'Invalid email address'
        : undefined

const required = value => (value || typeof value === 'number' ? undefined : 'Required')

export const LoginPage = (props) => {

    const renderField = ({
        input,
        label,
        type,
        meta: { touched, error, warning }
    }) => (
            <div className="loginField">
                <label>{label}</label>
                <div >
                    <input {...input} className="form-control" placeholder={label} type={type} />
                    {touched &&
                        ((error && <span style={{ color: "red" }}>{error}</span>) ||
                            (warning && <span>{warning}</span>))}
                </div>
            </div>
        );

    return (
        <div className="loginFormWrapper">
            <form className="loginForm" onSubmit={(event) => console.log(event)}>
                <div>
                    <h3>I have an account</h3>
                </div>
                <div>
                    <Field
                        label="Your Email"
                        name="email"
                        type="email"
                        component={renderField}
                        validate={[required, email]}
                    />
                </div>

                <div>
                    <Field
                        label="Your Password"
                        name="password"
                        type="password"
                        component={renderField}
                        validate={[required]}
                    />
                </div>

                <div>
                    <Button variant="danger">
                        Log in
                    </Button>
                </div>
            </form>
            <div className="registerInfo">
                <h3>Don't you have an account yet ?</h3>
                <p>Don't wait and register</p>
                <Button variant="danger">
                        Register
                </Button>
            </div>
            
        </div>
    )
}

const validate = (values) => {
    const errors = {};

    return errors;
}

export default reduxForm({
    validate,
    form: 'loginform'
})(LoginPage)
