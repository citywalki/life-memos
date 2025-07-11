// src/i18n.js
import { addMessages } from 'svelte-i18n';

import en from './en.json';
import zhCN from './zh-CN.json';

addMessages('en', en);
addMessages('zh-CN', zhCN);
