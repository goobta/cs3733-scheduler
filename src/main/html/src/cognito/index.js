import Amplify, { Auth } from "aws-amplify";

// Amplify.configure({
//     Auth: {
//         userPoolId: 'us-east-2_EcD6RdoUX',		      
//        	region: 'us-east-2',
//         identityPoolId: 'us-east-2:327926493007',
//         userPoolWebClientId: '1096af7rbo10p6sbjc3dledg3i',
//     }
// });

export default {
	async signup (username, email, password) {
		return await Auth.signUp({
			username,
			password,
			attributes: {
				email
			}
		})
	},
	async confirm (username, code) {
		return await Auth.confirmSignUp(username, code);
	},
	async login (username, password) {
		return await Auth.signIn(username, password);
	}
}