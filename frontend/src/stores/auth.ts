import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token') || '');
  const isAuthenticated = ref(!!token.value);

  function setToken(newToken: string) {
    token.value = newToken;
    isAuthenticated.value = true;
    localStorage.setItem('token', newToken);
  }

  function logout() {
    token.value = '';
    isAuthenticated.value = false;
    localStorage.removeItem('token');
  }

  return { token, isAuthenticated, setToken, logout };
});
