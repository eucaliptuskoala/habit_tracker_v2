import {useState} from 'react';

function SignUp(){
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [message, setMessage] = useState("");
    const [success, SetSuccess] = useState(true);

    const handleSubmit = (e) => {
        e.preventDefault();
        setTimeout(() => {
            if(success){
                setMessage(`User signed up successfully! ${name} - ${email}`);
                setName("");
                setEmail("");
            }
            else{
                setMessage("Error signing up user. Please try again.");
            }
        }, 1000);
    }
    return (
        <div>
        <form onSubmit={handleSubmit}>
            <input 
            type="text" 
            value={name} 
            onChange={(e) => setName(e.target.value)} 
            placeholder="Name" 
            />
            <input 
            type="email" 
            value={email} 
            onChange={(e) => setEmail(e.target.value)} 
            placeholder="Email" 
            />
            <button type="submit">Sign Up</button>
        </form>
        <p>{message}</p>
        </div>
    );
}

export default SignUp;