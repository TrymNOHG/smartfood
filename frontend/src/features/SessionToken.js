import {useLoggedInStore} from "@/store/store";
import router from "@/router/router";

export default async function sessionToken () {
    const sessionToken = useLoggedInStore().getSessionToken
    if (sessionToken === null) {
        alert("Log in to access your profile!") //TODO: make better
        await router.push("/login")
        throw new Error("Session token cannot be null. Login in again.")
    }

    return sessionToken
}