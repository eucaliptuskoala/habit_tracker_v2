import {defineConfig} from 'vitest/config';
import react from '@vitejs/plugin-react';

export default defineConfig({
    plugins: [react()],
    test: {
        environment: 'jsdom',
        coverage: {
            provider: "v8",
            reportsDirectory: "coverage",
            reporter: ["text", "lcov"],
        },
        exclude: [
            '**/node_modules/**',
            '**/dist/**',
            '**/e2e/**',
            '**/*.e2e.*'
        ],
    }
});
