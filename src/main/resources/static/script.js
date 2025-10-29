const API_BASE = window.location.origin + '/api';
let isLoading = false;

function showError(elementId, message) {
    const errorEl = document.getElementById(elementId);
    if (errorEl) {
        errorEl.textContent = message;
        errorEl.classList.remove('hidden');
    }
    console.error(`[${elementId}] ${message} at ${new Date().toLocaleString('en-IN', { timeZone: 'Asia/Kolkata' })}`);
}

function clearErrors() {
    document.querySelectorAll('.error').forEach(el => {
        if (el) {
            el.textContent = '';
            el.classList.add('hidden');
        }
    });
}

function setLoading(button, state) {
    isLoading = state;
    button.disabled = state;
    button.classList.toggle('loading', state);
}

async function fetchActiveServers() {
    const tableIdInput = document.getElementById('tableId');
    const button = document.querySelector('#user-form button');
    if (!tableIdInput) {
        showError('user-error', 'Table ID input not found');
        return;
    }
    const tableId = tableIdInput.value;
    if (!tableId) {
        showError('user-error', 'Please enter a valid Table ID');
        return;
    }

    setLoading(button, true);
    console.log(`Fetching active servers for tableId ${tableId} at ${new Date().toLocaleString('en-IN', { timeZone: 'Asia/Kolkata' })}`);
    try {
        const response = await fetch(`${API_BASE}/servers/${tableId}/active`);
        if (!response.ok) throw new Error(`Server error: ${response.status} - ${await response.text()}`);
        const servers = await response.json();
        if (!Array.isArray(servers) || servers.length === 0) throw new Error('No active servers found');
        const select = document.getElementById('serverId');
        if (!select) throw Error('Dropdown element #serverId not found');
        select.innerHTML = '<option value="">Select a server</option>';
        servers.forEach(server => {
            if (server.id || server._id) {
                const option = document.createElement('option');
                option.value = server.id || server._id;
                option.textContent = server.name || 'Unnamed Server';
                select.appendChild(option);
            } else {
                console.warn('Invalid server data:', server);
            }
        });
        document.getElementById('tip-form').classList.remove('hidden');
    } catch (error) {
        console.error('Fetch error:', error);
        showError('user-error', `Failed to load servers: ${error.message}`);
    } finally {
        setLoading(button, false);
    }
}

async function createTipAndPay() {
    const tableIdInput = document.getElementById('tableId');
    const serverIdInput = document.getElementById('serverId');
    const amountInput = document.getElementById('tipAmount');
    const button = document.querySelector('#tip-form button');
    if (!tableIdInput || !serverIdInput || !amountInput) {
        showError('tip-error', 'Form elements not found');
        return;
    }
    const tableId = tableIdInput.value;
    const serverId = serverIdInput.value;
    const amount = parseFloat(amountInput.value);

    if (!tableId || !serverId || !amount || amount <= 0) {
        return showError('tip-error', 'Please fill all fields with valid values');
    }

    setLoading(button, true);
    console.log(`Creating tip: { tableId: ${tableId}, serverId: ${serverId}, amount: ${amount} } at ${new Date().toLocaleString('en-IN', { timeZone: 'Asia/Kolkata' })}`);
    try {
        const response = await fetch(`${API_BASE}/tips`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ tableId, serverId, amount })
        });
        if (!response.ok) throw new Error(`Tip creation failed: ${response.status} - ${await response.text()}`);
        const tip = await response.json();
        if (!tip.razorpayOrderId) throw new Error('Missing razorpayOrderId in response');

        const options = {
            key: 'rzp_test_KOnLux8aD2tfJ8',
            amount: amount * 100,
            currency: 'INR',
            name: 'QuickTip',
            description: `Tip for Table ${tableId}`,
            order_id: tip.razorpayOrderId,
            handler: async function (paymentResponse) {
                try {
                    const statusResponse = await fetch(`${API_BASE}/tips/${tip.id}/status?status=SUCCESS`, { method: 'PUT' });
                    if (!statusResponse.ok) throw new Error('Failed to update tip status');
                    alert(`Payment Successful! Tip ID: ${tip.id} at ${new Date().toLocaleString('en-IN', { timeZone: 'Asia/Kolkata' })}`);
                    document.getElementById('tip-form').classList.add('hidden');
                    document.getElementById('user-form').reset();
                } catch (error) {
                    alert(`Error updating tip status: ${error.message}`);
                }
            },
            prefill: { name: 'Customer', email: 'customer@example.com' },
            theme: { color: '#4CAF50' }
        };
        const rzp = new Razorpay(options);
        rzp.on('payment.failed', function (response) {
            alert(`Payment Failed: ${response.error.description}`);
            fetch(`${API_BASE}/tips/${tip.id}/status?status=FAILED`, { method: 'PUT' });
        });
        rzp.open();
    } catch (error) {
        console.error('Payment error:', error);
        showError('tip-error', `Payment initiation failed: ${error.message}`);
    } finally {
        setLoading(button, false);
    }
}

window.onload = () => {
    const urlParams = new URLSearchParams(window.location.search);
    const tableId = urlParams.get('tableId');
    if (tableId) {
        document.getElementById('tableId').value = tableId;
        fetchActiveServers();
    }
};