/* create multi language component */
import { createI18n } from "vue-i18n";
import en from "./en.json"
import no from "./no.json"


const i18n = createI18n({
    locale: "en",
    messages: {
        en: en,
        no: no,
    },
});

export default i18n;
