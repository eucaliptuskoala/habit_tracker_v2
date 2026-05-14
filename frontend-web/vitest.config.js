import {defineConfig} from 'vitest/config'; 

export default defineConfig({
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