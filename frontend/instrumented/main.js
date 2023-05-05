function cov_rxl4ploo5() {
  var path = "C:\\Users\\Tomas\\systemutvikling\\idatt2106_2023_6\\frontend\\src\\main.js";
  var hash = "119a977b6b6347f9641b669b3f5d81935a1a981b";
  var global = new Function("return this")();
  var gcv = "__coverage__";
  var coverageData = {
    path: "C:\\Users\\Tomas\\systemutvikling\\idatt2106_2023_6\\frontend\\src\\main.js",
    statementMap: {
      "0": {
        start: {
          line: 26,
          column: 0
        },
        end: {
          line: 39,
          column: 1
        }
      },
      "1": {
        start: {
          line: 41,
          column: 14
        },
        end: {
          line: 41,
          column: 27
        }
      },
      "2": {
        start: {
          line: 43,
          column: 0
        },
        end: {
          line: 43,
          column: 37
        }
      },
      "3": {
        start: {
          line: 45,
          column: 0
        },
        end: {
          line: 50,
          column: 17
        }
      }
    },
    fnMap: {},
    branchMap: {},
    s: {
      "0": 0,
      "1": 0,
      "2": 0,
      "3": 0
    },
    f: {},
    b: {},
    _coverageSchema: "1a1c01bbd47fc00a2c39e90264f33305004495a9",
    hash: "119a977b6b6347f9641b669b3f5d81935a1a981b"
  };
  var coverage = global[gcv] || (global[gcv] = {});
  if (!coverage[path] || coverage[path].hash !== hash) {
    coverage[path] = coverageData;
  }
  var actualCoverage = coverage[path];
  {
    // @ts-ignore
    cov_rxl4ploo5 = function () {
      return actualCoverage;
    };
  }
  return actualCoverage;
}
cov_rxl4ploo5();
import { createApp } from 'vue';
import { createPinia } from 'pinia';
import piniaPluginPersistedState from "pinia-plugin-persistedstate";
import App from './App.vue';
import './assets/main.css';
import router from "../src/router/router";
/* Icons imports */
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faHouse, faKitchenSet, faShoppingCart, faEnvelope, faLock, faUtensils, faUserCircle, faPerson, faSignature, faPieChart, faPenToSquare, faTrash, faCircleCheck, faPlus, faCrown, faCamera, faBarcode, faCheck, faTableCells, faList, faFilter, faReceipt, faChartSimple, faCoins, faFlag, faBell, faGlobe } from "@fortawesome/free-solid-svg-icons";

/* Imports fr multiple languages */
import i18n from "@/locales/i18n";

/* add icons to the library */
cov_rxl4ploo5().s[0]++;
library.add(faUserCircle, faHouse, faKitchenSet, faShoppingCart, faEnvelope, faLock, faUtensils, faUserCircle, faPerson, faSignature, faPieChart, faPenToSquare, faTrash, faCircleCheck, faPlus, faCrown, faCheck, faTableCells, faList, faFilter, faReceipt, faChartSimple, faCoins, faFlag, faBell, faGlobe, faReceipt, faChartSimple, faCoins, faFlag, faCamera, faBarcode);
const pinia = (cov_rxl4ploo5().s[1]++, createPinia());
cov_rxl4ploo5().s[2]++;
pinia.use(piniaPluginPersistedState);
cov_rxl4ploo5().s[3]++;
createApp(App).use(pinia).use(i18n).use(router).component("font-awesome-icon", FontAwesomeIcon).mount("#app");
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJuYW1lcyI6WyJjb3ZfcnhsNHBsb281IiwiYWN0dWFsQ292ZXJhZ2UiLCJjcmVhdGVBcHAiLCJjcmVhdGVQaW5pYSIsInBpbmlhUGx1Z2luUGVyc2lzdGVkU3RhdGUiLCJBcHAiLCJyb3V0ZXIiLCJsaWJyYXJ5IiwiRm9udEF3ZXNvbWVJY29uIiwiZmFIb3VzZSIsImZhS2l0Y2hlblNldCIsImZhU2hvcHBpbmdDYXJ0IiwiZmFFbnZlbG9wZSIsImZhTG9jayIsImZhVXRlbnNpbHMiLCJmYVVzZXJDaXJjbGUiLCJmYVBlcnNvbiIsImZhU2lnbmF0dXJlIiwiZmFQaWVDaGFydCIsImZhUGVuVG9TcXVhcmUiLCJmYVRyYXNoIiwiZmFDaXJjbGVDaGVjayIsImZhUGx1cyIsImZhQ3Jvd24iLCJmYUNhbWVyYSIsImZhQmFyY29kZSIsImZhQ2hlY2siLCJmYVRhYmxlQ2VsbHMiLCJmYUxpc3QiLCJmYUZpbHRlciIsImZhUmVjZWlwdCIsImZhQ2hhcnRTaW1wbGUiLCJmYUNvaW5zIiwiZmFGbGFnIiwiZmFCZWxsIiwiZmFHbG9iZSIsImkxOG4iLCJzIiwiYWRkIiwicGluaWEiLCJ1c2UiLCJjb21wb25lbnQiLCJtb3VudCJdLCJzb3VyY2VzIjpbIm1haW4uanMiXSwic291cmNlc0NvbnRlbnQiOlsiaW1wb3J0IHsgY3JlYXRlQXBwIH0gZnJvbSAndnVlJ1xyXG5pbXBvcnQgeyBjcmVhdGVQaW5pYSB9IGZyb20gJ3BpbmlhJ1xyXG5pbXBvcnQgcGluaWFQbHVnaW5QZXJzaXN0ZWRTdGF0ZSBmcm9tIFwicGluaWEtcGx1Z2luLXBlcnNpc3RlZHN0YXRlXCJcclxuXHJcbmltcG9ydCBBcHAgZnJvbSAnLi9BcHAudnVlJ1xyXG5pbXBvcnQgJy4vYXNzZXRzL21haW4uY3NzJ1xyXG5pbXBvcnQgcm91dGVyIGZyb20gXCIuLi9zcmMvcm91dGVyL3JvdXRlclwiO1xyXG4vKiBJY29ucyBpbXBvcnRzICovXHJcbmltcG9ydCB7IGxpYnJhcnkgfSBmcm9tICdAZm9ydGF3ZXNvbWUvZm9udGF3ZXNvbWUtc3ZnLWNvcmUnXHJcbmltcG9ydCB7IEZvbnRBd2Vzb21lSWNvbiB9IGZyb20gJ0Bmb3J0YXdlc29tZS92dWUtZm9udGF3ZXNvbWUnXHJcblxyXG5pbXBvcnQge1xyXG4gICAgZmFIb3VzZSxcclxuICAgIGZhS2l0Y2hlblNldCwgZmFTaG9wcGluZ0NhcnQsIGZhRW52ZWxvcGUsXHJcbiAgICBmYUxvY2ssIGZhVXRlbnNpbHMsIGZhVXNlckNpcmNsZSwgZmFQZXJzb24sXHJcbiAgICBmYVNpZ25hdHVyZSwgZmFQaWVDaGFydCwgZmFQZW5Ub1NxdWFyZSwgZmFUcmFzaCxcclxuICAgIGZhQ2lyY2xlQ2hlY2ssIGZhUGx1cywgZmFDcm93biwgZmFDYW1lcmEsIGZhQmFyY29kZSxcclxuICAgIGZhQ2hlY2ssIGZhVGFibGVDZWxscywgZmFMaXN0LCBmYUZpbHRlcixcclxuICAgIGZhUmVjZWlwdCwgZmFDaGFydFNpbXBsZSwgZmFDb2lucywgZmFGbGFnLCBmYUJlbGwsIGZhR2xvYmUsXHJcbn0gZnJvbSBcIkBmb3J0YXdlc29tZS9mcmVlLXNvbGlkLXN2Zy1pY29uc1wiO1xyXG5cclxuLyogSW1wb3J0cyBmciBtdWx0aXBsZSBsYW5ndWFnZXMgKi9cclxuaW1wb3J0IGkxOG4gZnJvbSBcIkAvbG9jYWxlcy9pMThuXCI7XHJcblxyXG4vKiBhZGQgaWNvbnMgdG8gdGhlIGxpYnJhcnkgKi9cclxubGlicmFyeS5hZGQoXHJcbiAgICBmYVVzZXJDaXJjbGUsIGZhSG91c2UsXHJcbiAgICBmYUtpdGNoZW5TZXQsIGZhU2hvcHBpbmdDYXJ0LFxyXG4gICAgZmFFbnZlbG9wZSwgZmFMb2NrLFxyXG4gICAgZmFVdGVuc2lscywgZmFVc2VyQ2lyY2xlLFxyXG4gICAgZmFQZXJzb24sIGZhU2lnbmF0dXJlLFxyXG4gICAgZmFQaWVDaGFydCwgZmFQZW5Ub1NxdWFyZSxcclxuICAgIGZhVHJhc2gsIGZhQ2lyY2xlQ2hlY2ssXHJcbiAgICBmYVBsdXMsIGZhQ3Jvd24sIGZhQ2hlY2ssXHJcbiAgICBmYVRhYmxlQ2VsbHMsIGZhTGlzdCwgZmFGaWx0ZXIsXHJcbiAgICBmYVJlY2VpcHQsIGZhQ2hhcnRTaW1wbGUsIGZhQ29pbnMsIGZhRmxhZyxcclxuICAgIGZhQmVsbCwgZmFHbG9iZSwgZmFSZWNlaXB0LCBmYUNoYXJ0U2ltcGxlLFxyXG4gICAgZmFDb2lucywgZmFGbGFnLCBmYUNhbWVyYSxmYUJhcmNvZGVcclxuKVxyXG5cclxuY29uc3QgcGluaWEgPSBjcmVhdGVQaW5pYSgpO1xyXG5cclxucGluaWEudXNlKHBpbmlhUGx1Z2luUGVyc2lzdGVkU3RhdGUpO1xyXG5cclxuY3JlYXRlQXBwKEFwcClcclxuICAudXNlKHBpbmlhKVxyXG4gIC51c2UoaTE4bilcclxuICAudXNlKHJvdXRlcilcclxuICAuY29tcG9uZW50KFwiZm9udC1hd2Vzb21lLWljb25cIiwgRm9udEF3ZXNvbWVJY29uKVxyXG4gIC5tb3VudChcIiNhcHBcIik7XHJcbiJdLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7SUFlWTtJQUFBQSxhQUFBLFlBQUFBLENBQUE7TUFBQSxPQUFBQyxjQUFBO0lBQUE7RUFBQTtFQUFBLE9BQUFBLGNBQUE7QUFBQTtBQUFBRCxhQUFBO0FBZlosU0FBU0UsU0FBUyxRQUFRLEtBQUs7QUFDL0IsU0FBU0MsV0FBVyxRQUFRLE9BQU87QUFDbkMsT0FBT0MseUJBQXlCLE1BQU0sNkJBQTZCO0FBRW5FLE9BQU9DLEdBQUcsTUFBTSxXQUFXO0FBQzNCLE9BQU8sbUJBQW1CO0FBQzFCLE9BQU9DLE1BQU0sTUFBTSxzQkFBc0I7QUFDekM7QUFDQSxTQUFTQyxPQUFPLFFBQVEsbUNBQW1DO0FBQzNELFNBQVNDLGVBQWUsUUFBUSw4QkFBOEI7QUFFOUQsU0FDSUMsT0FBTyxFQUNQQyxZQUFZLEVBQUVDLGNBQWMsRUFBRUMsVUFBVSxFQUN4Q0MsTUFBTSxFQUFFQyxVQUFVLEVBQUVDLFlBQVksRUFBRUMsUUFBUSxFQUMxQ0MsV0FBVyxFQUFFQyxVQUFVLEVBQUVDLGFBQWEsRUFBRUMsT0FBTyxFQUMvQ0MsYUFBYSxFQUFFQyxNQUFNLEVBQUVDLE9BQU8sRUFBRUMsUUFBUSxFQUFFQyxTQUFTLEVBQ25EQyxPQUFPLEVBQUVDLFlBQVksRUFBRUMsTUFBTSxFQUFFQyxRQUFRLEVBQ3ZDQyxTQUFTLEVBQUVDLGFBQWEsRUFBRUMsT0FBTyxFQUFFQyxNQUFNLEVBQUVDLE1BQU0sRUFBRUMsT0FBTyxRQUN2RCxtQ0FBbUM7O0FBRTFDO0FBQ0EsT0FBT0MsSUFBSSxNQUFNLGdCQUFnQjs7QUFFakM7QUFBQXBDLGFBQUEsR0FBQXFDLENBQUE7QUFDQTlCLE9BQU8sQ0FBQytCLEdBQUcsQ0FDUHZCLFlBQVksRUFBRU4sT0FBTyxFQUNyQkMsWUFBWSxFQUFFQyxjQUFjLEVBQzVCQyxVQUFVLEVBQUVDLE1BQU0sRUFDbEJDLFVBQVUsRUFBRUMsWUFBWSxFQUN4QkMsUUFBUSxFQUFFQyxXQUFXLEVBQ3JCQyxVQUFVLEVBQUVDLGFBQWEsRUFDekJDLE9BQU8sRUFBRUMsYUFBYSxFQUN0QkMsTUFBTSxFQUFFQyxPQUFPLEVBQUVHLE9BQU8sRUFDeEJDLFlBQVksRUFBRUMsTUFBTSxFQUFFQyxRQUFRLEVBQzlCQyxTQUFTLEVBQUVDLGFBQWEsRUFBRUMsT0FBTyxFQUFFQyxNQUFNLEVBQ3pDQyxNQUFNLEVBQUVDLE9BQU8sRUFBRUwsU0FBUyxFQUFFQyxhQUFhLEVBQ3pDQyxPQUFPLEVBQUVDLE1BQU0sRUFBRVQsUUFBUSxFQUFDQyxTQUM5QixDQUFDO0FBRUQsTUFBTWMsS0FBSyxJQUFBdkMsYUFBQSxHQUFBcUMsQ0FBQSxPQUFHbEMsV0FBVyxDQUFDLENBQUM7QUFBQ0gsYUFBQSxHQUFBcUMsQ0FBQTtBQUU1QkUsS0FBSyxDQUFDQyxHQUFHLENBQUNwQyx5QkFBeUIsQ0FBQztBQUFDSixhQUFBLEdBQUFxQyxDQUFBO0FBRXJDbkMsU0FBUyxDQUFDRyxHQUFHLENBQUMsQ0FDWG1DLEdBQUcsQ0FBQ0QsS0FBSyxDQUFDLENBQ1ZDLEdBQUcsQ0FBQ0osSUFBSSxDQUFDLENBQ1RJLEdBQUcsQ0FBQ2xDLE1BQU0sQ0FBQyxDQUNYbUMsU0FBUyxDQUFDLG1CQUFtQixFQUFFakMsZUFBZSxDQUFDLENBQy9Da0MsS0FBSyxDQUFDLE1BQU0sQ0FBQyJ9