import { createI18n } from 'vue-i18n'
import en from "./en.json"
import no from "./no.json"


const i18n = createI18n({
    legacy: false,
    locale: "NO",
    messages: {
        EN: en,
        NO: no,
    },
});

export default i18n;
