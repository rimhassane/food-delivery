import React, { createContext, useState, useContext, useEffect } from 'react';

const AuthContext = createContext(null);

const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Vérifier si l'utilisateur est déjà connecté
    const checkAuth = async () => {
      try {
        const storedUser = localStorage.getItem('user');
        if (storedUser) {
          setUser(JSON.parse(storedUser));
        }
      } catch (err) {
        console.error('Erreur lors de la vérification de l\'authentification:', err);
        setError('Erreur lors du chargement de la session utilisateur');
      } finally {
        setLoading(false);
      }
    };

    checkAuth();
  }, []);

  const login = async (email, password) => {
    try {
      setLoading(true);
      setError(null);
      
      // Simulation d'un appel API
      // Remplacez ceci par un véritable appel à votre API
      await new Promise(resolve => setTimeout(resolve, 500)); // Simulation de délai réseau
      
      // Vérification simulée des identifiants
      if (!email || !password) {
        throw new Error('Email et mot de passe sont requis');
      }
      
      // En production, vous feriez quelque chose comme :
      // const response = await fetch('/api/auth/login', {
      //   method: 'POST',
      //   headers: { 'Content-Type': 'application/json' },
      //   body: JSON.stringify({ email, password })
      // });
      // const data = await response.json();
      // if (!response.ok) throw new Error(data.message || 'Échec de la connexion');
      
      const mockUser = { 
        id: '1', 
        email, 
        name: email.split('@')[0], // Utilise la partie avant @ comme nom
        token: 'mock-jwt-token'
      };
      
      setUser(mockUser);
      localStorage.setItem('user', JSON.stringify(mockUser));
      return mockUser;
    } catch (err) {
      console.error('Erreur de connexion:', err);
      setError(err.message || 'Une erreur est survenue lors de la connexion');
      throw err;
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    try {
      // En production, vous pourriez vouloir appeler un endpoint de déconnexion
      // await fetch('/api/auth/logout', { method: 'POST' });
      
      setUser(null);
      localStorage.removeItem('user');
    } catch (err) {
      console.error('Erreur lors de la déconnexion:', err);
      setError('Erreur lors de la déconnexion');
    }
  };

  return (
    <AuthContext.Provider 
      value={{ 
        user, 
        isAuthenticated: !!user,
        login, 
        logout, 
        loading, 
        error 
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth doit être utilisé à l\'intérieur d\'un AuthProvider');
  }
  return context;
};

export { AuthProvider, useAuth };
export default AuthContext;
